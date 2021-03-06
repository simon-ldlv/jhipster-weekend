import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Weekend } from './weekend.model';
import { WeekendService } from './weekend.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-weekend',
    templateUrl: './weekend.component.html'
})
export class WeekendComponent implements OnInit, OnDestroy {
    weekends: Weekend[];
    currentAccount: any;
    eventSubscriber: Subscription;
    dateSaturday: string;
    

    constructor(
        private weekendService: WeekendService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.weekendService.query().subscribe(
            (res: ResponseWrapper) => {
                this.weekends = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        var today = new Date();
        var nextSaturday;
        var stop = false;
        for(var i = 0 ; i < 7 && !stop; i++){
            nextSaturday = new Date(today.getFullYear(), today.getMonth(), today.getDate() + i);
            if(nextSaturday.getDay() == 6) {
                stop = true;    
            }    
        }
        this.dateSaturday = nextSaturday.toLocaleString().split(' ')[0];
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInWeekends();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWeekends() {
        this.eventSubscriber = this.eventManager.subscribe('weekendListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
