import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pratique } from './pratique.model';
import { PratiquePopupService } from './pratique-popup.service';
import { PratiqueService } from './pratique.service';
import { Activite, ActiviteService } from '../activite';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

import { Account, LoginModalService, Principal } from '../../shared';


@Component({
    selector: 'jhi-pratique-dialog',
    templateUrl: './pratique-dialog.component.html'
})
export class PratiqueDialogComponent implements OnInit {

    pratique: Pratique;
    isSaving: boolean;

    account: Account;
    activites: Activite[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pratiqueService: PratiqueService,
        private activiteService: ActiviteService,
        private userService: UserService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private loginModalService: LoginModalService
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activiteService.query()
            .subscribe((res: ResponseWrapper) => { this.activites = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.principal.identity().then((account) => {
            this.account = account;
        });
   
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pratique.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pratiqueService.update(this.pratique));
        } else {
            this.subscribeToSaveResponse(
                this.pratiqueService.create(this.pratique));
        }
    }

    private subscribeToSaveResponse(result: Observable<Pratique>) {
        result.subscribe((res: Pratique) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Pratique) {
        this.eventManager.broadcast({ name: 'pratiqueListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pratique-popup',
    template: ''
})
export class PratiquePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pratiquePopupService: PratiquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pratiquePopupService
                    .open(PratiqueDialogComponent as Component, params['id']);
            } else {
                this.pratiquePopupService
                    .open(PratiqueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
