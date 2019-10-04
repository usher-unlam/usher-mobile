package com.usher.usher.presenters;

import com.usher.usher.interactors.MainMenuActivityInteractorImpl;
import com.usher.usher.interfaces.MainMenuActivityInteractor;
import com.usher.usher.interfaces.MainMenuActivityPresenter;
import com.usher.usher.interfaces.MainMenuActivityView;

public class MainMenuActivityPresenterImpl implements MainMenuActivityPresenter {

    private MainMenuActivityView view;
    private MainMenuActivityInteractor interactor;

    public MainMenuActivityPresenterImpl( MainMenuActivityView view){
        //Por aca recibe lo de la vista con sus parametros como constructor
        this.view = view;
        //Por aca paso los metodos del presentador al interactor
        interactor = new MainMenuActivityInteractorImpl(this);
    }

    @Override
    public void checkSesion() {
        view.showProgress(true);
        interactor.checkSesionStatus();
    }

    @Override
    public void onSesion(boolean status) {
        if (view != null) {
            view.showProgress(false);
            view.onSesion(status);
        }
    }

    @Override
    public void offSesion(boolean status) {
        if (view != null) {
            view.showProgress(false);
            view.offSesion(status);
        }
    }
}