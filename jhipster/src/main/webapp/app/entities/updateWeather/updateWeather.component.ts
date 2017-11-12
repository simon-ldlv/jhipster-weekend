import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { UpdateWeather } from './updateWeather.model';
import { UpdateWeatherService } from './updateWeather.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-updateWeather',
    templateUrl: './updateWeather.component.html'
})
export class UpdateWeatherComponent implements OnInit, OnDestroy {
    updateWeathers: UpdateWeather[];
    currentAccount: any;
    eventSubscriber: Subscription;
    

    constructor(
        private updateWeatherService: UpdateWeatherService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.updateWeatherService.query().subscribe(
            (res: ResponseWrapper) => {
                this.updateWeathers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUpdateWeathers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUpdateWeathers() {
        this.eventSubscriber = this.eventManager.subscribe('updateWeatherListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
