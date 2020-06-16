//package io.github.nearchos.archcomp.iss_position;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Transformations;
//import androidx.lifecycle.ViewModel;
//
//public class IssPositionViewModel extends ViewModel {
//
//    private MutableLiveData<IssPosition> issPositionMutableLiveData
//            = new MutableLiveData<>();
//
//    // must set Java version 8 or higher to enable lambda expressions
//    private LiveData<IssPosition> issPositionLiveData
//            = Transformations.map(issPositionMutableLiveData, input -> input);
//
//    LiveData<IssPosition> getIssPosition() {
//        return issPositionLiveData;
//    }
//
//    public void setIssPosition(final IssPosition issPosition) {
//        issPositionMutableLiveData.setValue(issPosition);
//    }
//}