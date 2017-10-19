import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Meteo } from './meteo.model';
import { MeteoPopupService } from './meteo-popup.service';
import { MeteoService } from './meteo.service';
import { Ville, VilleService } from '../ville';
import { Weather, WeatherService } from '../weather';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-meteo-dialog',
    templateUrl: './meteo-dialog.component.html'
})
export class MeteoDialogComponent implements OnInit {

    meteo: Meteo;
    isSaving: boolean;

    villes: Ville[];

    weathers: Weather[];
    updatedDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private meteoService: MeteoService,
        private villeService: VilleService,
        private weatherService: WeatherService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.villeService.query()
            .subscribe((res: ResponseWrapper) => { this.villes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.weatherService.query()
            .subscribe((res: ResponseWrapper) => { this.weathers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.meteo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.meteoService.update(this.meteo));
        } else {
            this.subscribeToSaveResponse(
                this.meteoService.create(this.meteo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Meteo>) {
        result.subscribe((res: Meteo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Meteo) {
        this.eventManager.broadcast({ name: 'meteoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackVilleById(index: number, item: Ville) {
        return item.id;
    }

    trackWeatherById(index: number, item: Weather) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-meteo-popup',
    template: ''
})
export class MeteoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private meteoPopupService: MeteoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.meteoPopupService
                    .open(MeteoDialogComponent as Component, params['id']);
            } else {
                this.meteoPopupService
                    .open(MeteoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
