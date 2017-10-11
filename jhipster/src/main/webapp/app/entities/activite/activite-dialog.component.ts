import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Activite } from './activite.model';
import { ActivitePopupService } from './activite-popup.service';
import { ActiviteService } from './activite.service';
import { Ville, VilleService } from '../ville';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-activite-dialog',
    templateUrl: './activite-dialog.component.html'
})
export class ActiviteDialogComponent implements OnInit {

    activite: Activite;
    isSaving: boolean;

    villes: Ville[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private activiteService: ActiviteService,
        private villeService: VilleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.villeService.query()
            .subscribe((res: ResponseWrapper) => { this.villes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.activite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.activiteService.update(this.activite));
        } else {
            this.subscribeToSaveResponse(
                this.activiteService.create(this.activite));
        }
    }

    private subscribeToSaveResponse(result: Observable<Activite>) {
        result.subscribe((res: Activite) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Activite) {
        this.eventManager.broadcast({ name: 'activiteListModification', content: 'OK'});
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
    selector: 'jhi-activite-popup',
    template: ''
})
export class ActivitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activitePopupService: ActivitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.activitePopupService
                    .open(ActiviteDialogComponent as Component, params['id']);
            } else {
                this.activitePopupService
                    .open(ActiviteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
