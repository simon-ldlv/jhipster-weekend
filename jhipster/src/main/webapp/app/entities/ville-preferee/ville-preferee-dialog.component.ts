import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VillePreferee } from './ville-preferee.model';
import { VillePrefereePopupService } from './ville-preferee-popup.service';
import { VillePrefereeService } from './ville-preferee.service';
import { Ville, VilleService } from '../ville';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ville-preferee-dialog',
    templateUrl: './ville-preferee-dialog.component.html'
})
export class VillePrefereeDialogComponent implements OnInit {

    villePreferee: VillePreferee;
    isSaving: boolean;

    villes: Ville[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private villePrefereeService: VillePrefereeService,
        private villeService: VilleService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.villeService.query()
            .subscribe((res: ResponseWrapper) => { this.villes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.villePreferee.id !== undefined) {
            this.subscribeToSaveResponse(
                this.villePrefereeService.update(this.villePreferee));
        } else {
            this.subscribeToSaveResponse(
                this.villePrefereeService.create(this.villePreferee));
        }
    }

    private subscribeToSaveResponse(result: Observable<VillePreferee>) {
        result.subscribe((res: VillePreferee) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VillePreferee) {
        this.eventManager.broadcast({ name: 'villePrefereeListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ville-preferee-popup',
    template: ''
})
export class VillePrefereePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private villePrefereePopupService: VillePrefereePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.villePrefereePopupService
                    .open(VillePrefereeDialogComponent as Component, params['id']);
            } else {
                this.villePrefereePopupService
                    .open(VillePrefereeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
