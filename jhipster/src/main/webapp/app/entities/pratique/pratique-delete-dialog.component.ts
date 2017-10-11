import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pratique } from './pratique.model';
import { PratiquePopupService } from './pratique-popup.service';
import { PratiqueService } from './pratique.service';

@Component({
    selector: 'jhi-pratique-delete-dialog',
    templateUrl: './pratique-delete-dialog.component.html'
})
export class PratiqueDeleteDialogComponent {

    pratique: Pratique;

    constructor(
        private pratiqueService: PratiqueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pratiqueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pratiqueListModification',
                content: 'Deleted an pratique'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pratique-delete-popup',
    template: ''
})
export class PratiqueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pratiquePopupService: PratiquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pratiquePopupService
                .open(PratiqueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
