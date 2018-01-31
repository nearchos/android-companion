package io.github.nearchos.handlingjson;

import java.util.Arrays;

public class Result {

    String [] destination_addresses;
    String [] origin_addresses;
    Row [] rows;
    String status;

    public Result(String[] destination_addresses, String[] origin_addresses, Row[] rows, String status) {
        this.destination_addresses = destination_addresses;
        this.origin_addresses = origin_addresses;
        this.rows = rows;
        this.status = status;
    }

    class Row {
        Element [] elements;

        public Row(Element[] elements) {
            this.elements = elements;
        }
    }

    class Element {
        Distance distance;
        Duration duration;
        String status;

        public Element(Distance distance, Duration duration, String status) {
            this.distance = distance;
            this.duration = duration;
            this.status = status;
        }
    }

    class Distance {
        String text;
        int value;

        public Distance(String text, int value) {
            this.text = text;
            this.value = value;
        }
    }

    class Duration {
        private String text;
        private int value;

        public Duration(String text, int value) {
            this.text = text;
            this.value = value;
        }
    }
}