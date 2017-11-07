import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VillePreferee } from './ville-preferee.model';
import { VillePrefereePopupService } from './ville-preferee-popup.service';
import { VillePrefereeService } from './ville-preferee.service';

@Component({
    selector: 'jhi-ville-preferee-delete-dialog',
    templateUrl: './ville-preferee-delete-dialog.component.html'
})
export class VillePrefereeDeleteDialogComponent {

    villePreferee: VillePreferee;

    constructor(
        private villePrefereeService: VillePrefereeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.villePrefereeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'villePrefereeListModification',
                content: 'Deleted an villePreferee'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ville-preferee-delete-popup',
    template: ''
})
export class VillePrefereeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private villePrefereePopupService: VillePrefereePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.villePrefereePopupService
                .open(VillePrefereeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
