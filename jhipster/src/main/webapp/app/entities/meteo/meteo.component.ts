import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Meteo } from './meteo.model';
import { MeteoService } from './meteo.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-meteo',
    templateUrl: './meteo.component.html'
})
export class MeteoComponent implements OnInit, OnDestroy {
meteos: Meteo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private meteoService: MeteoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.meteoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.meteos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMeteos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Meteo) {
        return item.id;
    }
    registerChangeInMeteos() {
        this.eventSubscriber = this.eventManager.subscribe('meteoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
