package io.github.nearchos.coffeelovers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;

import io.github.nearchos.coffeelovers.model.Coffee;
import io.github.nearchos.coffeelovers.model.Order;

/**
 * @author Nearchos
 * Created: 30-Mar-18
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "mad-book";

    public static final String DB_NAME = "coffee_lovers.db";
    public static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL("CREATE TABLE orders (id INTEGER PRIMARY KEY, name TEXT, type TEXT, size TEXT, caffeine_free INTEGER, milk TEXT, quantity INTEGER, status TEXT)");
        db.execSQL("CREATE TABLE prices (type TEXT, size TEXT, price REAL)");

        // initialize prices for the 3 coffee types and 3 sizes
        addPrice(db, Coffee.Type.CAPPUCCINO, Coffee.Size.SMALL, 2.10);
        addPrice(db, Coffee.Type.LATTE, Coffee.Size.SMALL, 2.20);
        addPrice(db, Coffee.Type.AMERICANO, Coffee.Size.SMALL, 2.30);
        addPrice(db, Coffee.Type.CAPPUCCINO, Coffee.Size.MEDIUM, 2.60);
        addPrice(db, Coffee.Type.LATTE, Coffee.Size.MEDIUM, 2.70);
        addPrice(db, Coffee.Type.AMERICANO, Coffee.Size.MEDIUM, 2.80);
        addPrice(db, Coffee.Type.CAPPUCCINO, Coffee.Size.LARGE, 3.10);
        addPrice(db, Coffee.Type.LATTE, Coffee.Size.LARGE, 3.20);
        addPrice(db, Coffee.Type.AMERICANO, Coffee.Size.LARGE, 3.30);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // nothing
    }

    public static void addOrEditOrder(SQLiteDatabase db, final Order order) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("name", order.getName());
        contentValues.put("type", order.getCoffee().getType().name());
        contentValues.put("size", order.getCoffee().getSize().name());
        contentValues.put("caffeine_free", order.getCoffee().isCaffeineFree() ? 1 : 0);
        contentValues.put("milk", order.getCoffee().getMilk().name());
        contentValues.put("quantity", order.getQuantity());
        contentValues.put("status", order.getStatus().name());

        // if the id is not zero, then it's an 'edit', otherwise it's an 'add'
        if(order.getId() != 0) { // edit
            db.update("orders", contentValues, "id=?", new String[] { Integer.toString(order.getId())});
        } else { // add
            db.insert("orders", null, contentValues);
        }
    }

    public static void addPrice(SQLiteDatabase db, final Coffee.Type type, Coffee.Size size, final double price) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("type", type.name());
        contentValues.put("size", size.name());
        contentValues.put("price", price);
        db.insert("prices", null, contentValues);
    }

    public static Vector<Order> getOrders(SQLiteDatabase db, final boolean openOrdersOnly) {
        final Vector<Order> orders = new Vector<>();

        final Cursor cursor;
        if(openOrdersOnly) {
            cursor = db.rawQuery("SELECT * FROM orders WHERE status=?", new String[] {Order.Status.OPEN.name()} );
        } else {
            cursor = db.rawQuery("SELECT * FROM orders", new String[0]);
        }

        final int idColumnIndex = cursor.getColumnIndex("id");
        final int nameColumnIndex = cursor.getColumnIndex("name");
        final int typeColumnIndex = cursor.getColumnIndex("type");
        final int sizeColumnIndex = cursor.getColumnIndex("size");
        final int caffeineFreeColumnIndex = cursor.getColumnIndex("caffeine_free");
        final int milkColumnIndex = cursor.getColumnIndex("milk");
        final int quantityColumnIndex = cursor.getColumnIndex("quantity");
        final int statusColumnIndex = cursor.getColumnIndex("status");

        while(cursor.moveToNext()) {
            final int id = cursor.getInt(idColumnIndex);
            final String name  = cursor.getString(nameColumnIndex);
            final String type = cursor.getString(typeColumnIndex);
            final String size = cursor.getString(sizeColumnIndex);
            final boolean caffeineFree = cursor.getInt(caffeineFreeColumnIndex) != 0;
            final String milk = cursor.getString(milkColumnIndex);
            final int quantity = cursor.getInt(quantityColumnIndex);
            final String status = cursor.getString(statusColumnIndex);

            final Order order = new Order(id, name, Coffee.Type.valueOf(type), Coffee.Size.valueOf(size), Coffee.Milk.valueOf(milk), caffeineFree, quantity, Order.Status.valueOf(status));
            orders.add(order);
        }

        cursor.close();

        return orders;
    }

    public static void closeOrder(SQLiteDatabase db, final int id) {
        db.execSQL("UPDATE orders SET status=? WHERE id=?", new Object[] {Order.Status.CLOSED.name(), id});
    }

    public static void openOrder(SQLiteDatabase db, final int id) {
        db.execSQL("UPDATE orders SET status=? WHERE id=?", new Object[] {Order.Status.OPEN.name(), id});
    }

    public static double getPrice(SQLiteDatabase db, final Coffee.Type type, Coffee.Size size) {
        final Cursor cursor = db.rawQuery("SELECT price FROM prices WHERE type=? AND size=?", new String[]{ type.name(), size.name() });
        if(cursor.getCount() < 1) {
            Log.e(TAG, "Could not find price fot given type/size: " + type + " / " + size);
            return -1;
        }

        final int priceColumnIndex = cursor.getColumnIndex("price");

        cursor.moveToFirst();
        final double price = cursor.getDouble(priceColumnIndex);

        cursor.close();

        return price;
    }
}