import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ville } from './ville.model';
import { VillePopupService } from './ville-popup.service';
import { VilleService } from './ville.service';
import { Activite, ActiviteService } from '../activite';
import { Meteo, MeteoService } from '../meteo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ville-dialog',
    templateUrl: './ville-dialog.component.html'
})
export class VilleDialogComponent implements OnInit {

    ville: Ville;
    isSaving: boolean;

    activites: Activite[];

    meteos: Meteo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private villeService: VilleService,
        private activiteService: ActiviteService,
        private meteoService: MeteoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activiteService.query()
            .subscribe((res: ResponseWrapper) => { this.activites = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.meteoService
            .query({filter: 'ville-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.ville.meteo || !this.ville.meteo.id) {
                    this.meteos = res.json;
                } else {
                    this.meteoService
                        .find(this.ville.meteo.id)
                        .subscribe((subRes: Meteo) => {
                            this.meteos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ville.id !== undefined) {
            this.subscribeToSaveResponse(
                this.villeService.update(this.ville));
        } else {
            this.subscribeToSaveResponse(
                this.villeService.create(this.ville));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ville>) {
        result.subscribe((res: Ville) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ville) {
        this.eventManager.broadcast({ name: 'villeListModification', content: 'OK'});
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

    trackMeteoById(index: number, item: Meteo) {
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
    selector: 'jhi-ville-popup',
    template: ''
})
export class VillePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private villePopupService: VillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.villePopupService
                    .open(VilleDialogComponent as Component, params['id']);
            } else {
                this.villePopupService
                    .open(VilleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
