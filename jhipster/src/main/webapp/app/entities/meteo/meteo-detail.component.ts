import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Meteo } from './meteo.model';
import { MeteoService } from './meteo.service';

@Component({
    selector: 'jhi-meteo-detail',
    templateUrl: './meteo-detail.component.html'
})
export class MeteoDetailComponent implements OnInit, OnDestroy {

    meteo: Meteo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private meteoService: MeteoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMeteos();
    }

    load(id) {
        this.meteoService.find(id).subscribe((meteo) => {
            this.meteo = meteo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMeteos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'meteoListModification',
            (response) => this.load(this.meteo.id)
        );
    }
}
