package ny.sujith.com.nytimes.presenter;


import java.util.List;

import ny.sujith.com.nytimes.model.Result;

/**
 * Created by Sujith Chandranon 09-11-2018.
 */

public interface HomePresenter {

    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface presenter{

        void onDestroy();

        void onRefreshButtonClick();

        void requestDataFromServer();

    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setDataToRecyclerView and onResponseFailure is fetched from the GetNoticeInteractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<Result> resultArrayList);

        void onResponseFailure(Throwable throwable);

    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetArticleIntractor {

        interface OnFinishedListener {
            void onFinished(List<Result> resultList);
            void onFailure(Throwable t);
        }

        void getArticleArrayList(OnFinishedListener onFinishedListener);
    }
}
