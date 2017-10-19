import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Weather } from './weather.model';
import { WeatherPopupService } from './weather-popup.service';
import { WeatherService } from './weather.service';

@Component({
    selector: 'jhi-weather-delete-dialog',
    templateUrl: './weather-delete-dialog.component.html'
})
export class WeatherDeleteDialogComponent {

    weather: Weather;

    constructor(
        private weatherService: WeatherService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.weatherService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'weatherListModification',
                content: 'Deleted an weather'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-weather-delete-popup',
    template: ''
})
export class WeatherDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private weatherPopupService: WeatherPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.weatherPopupService
                .open(WeatherDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
