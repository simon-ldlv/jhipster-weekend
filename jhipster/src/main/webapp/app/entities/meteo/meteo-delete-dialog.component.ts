import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Meteo } from './meteo.model';
import { MeteoPopupService } from './meteo-popup.service';
import { MeteoService } from './meteo.service';

@Component({
    selector: 'jhi-meteo-delete-dialog',
    templateUrl: './meteo-delete-dialog.component.html'
})
export class MeteoDeleteDialogComponent {

    meteo: Meteo;

    constructor(
        private meteoService: MeteoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.meteoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'meteoListModification',
                content: 'Deleted an meteo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-meteo-delete-popup',
    template: ''
})
export class MeteoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private meteoPopupService: MeteoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.meteoPopupService
                .open(MeteoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
