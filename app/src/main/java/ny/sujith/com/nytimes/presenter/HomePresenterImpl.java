package ny.sujith.com.nytimes.presenter;


import java.util.List;

import ny.sujith.com.nytimes.EspressoTestingIdlingResource;
import ny.sujith.com.nytimes.model.Result;

/**
 * Created by Sujith Chandranon 09-11-2018.
 */
public class HomePresenterImpl implements HomePresenter.presenter, HomePresenter.GetArticleIntractor
        .OnFinishedListener {

    private HomePresenter.MainView mainView;
    private HomePresenter.GetArticleIntractor getArticleIntractor;

    public HomePresenterImpl(HomePresenter.MainView mainView, HomePresenter.GetArticleIntractor

            getArticleIntractor) {
        this.mainView = mainView;
        this.getArticleIntractor = getArticleIntractor;
    }

    @Override
    public void onDestroy() {

        mainView = null;

    }

    @Override
    public void onRefreshButtonClick() {

        showProgress();
        getArticleIntractor.getArticleArrayList(this);

    }

    private void showProgress() {
        if (mainView != null) {
            mainView.showProgress();
        }
    }

    @Override
    public void requestDataFromServer() {
        EspressoTestingIdlingResource.increment();
        showProgress();

        getArticleIntractor.getArticleArrayList(this);
    }


    @Override
    public void onFinished(List<Result> resultList) {
        if (mainView != null) {
            mainView.setDataToRecyclerView(resultList);
            mainView.hideProgress();
        }

        EspressoTestingIdlingResource.decrement();
    }

    @Override
    public void onFailure(Throwable t) {
        if (mainView != null) {
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }

        EspressoTestingIdlingResource.decrement();
    }
}
