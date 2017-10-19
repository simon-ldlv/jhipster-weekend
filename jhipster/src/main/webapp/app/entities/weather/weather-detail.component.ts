import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Weather } from './weather.model';
import { WeatherService } from './weather.service';

@Component({
    selector: 'jhi-weather-detail',
    templateUrl: './weather-detail.component.html'
})
export class WeatherDetailComponent implements OnInit, OnDestroy {

    weather: Weather;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private weatherService: WeatherService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWeathers();
    }

    load(id) {
        this.weatherService.find(id).subscribe((weather) => {
            this.weather = weather;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWeathers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'weatherListModification',
            (response) => this.load(this.weather.id)
        );
    }
}
