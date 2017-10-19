import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Weather } from './weather.model';
import { WeatherPopupService } from './weather-popup.service';
import { WeatherService } from './weather.service';
import { Activite, ActiviteService } from '../activite';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-weather-dialog',
    templateUrl: './weather-dialog.component.html'
})
export class WeatherDialogComponent implements OnInit {

    weather: Weather;
    isSaving: boolean;

    activites: Activite[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private weatherService: WeatherService,
        private activiteService: ActiviteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activiteService.query()
            .subscribe((res: ResponseWrapper) => { this.activites = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.weather.id !== undefined) {
            this.subscribeToSaveResponse(
                this.weatherService.update(this.weather));
        } else {
            this.subscribeToSaveResponse(
                this.weatherService.create(this.weather));
        }
    }

    private subscribeToSaveResponse(result: Observable<Weather>) {
        result.subscribe((res: Weather) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Weather) {
        this.eventManager.broadcast({ name: 'weatherListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackActiviteById(index: number, item: Activite) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-weather-popup',
    template: ''
})
export class WeatherPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weatherPopupService: WeatherPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.weatherPopupService
                    .open(WeatherDialogComponent as Component, params['id']);
            } else {
                this.weatherPopupService
                    .open(WeatherDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
