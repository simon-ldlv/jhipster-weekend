import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Pratique } from './pratique.model';
import { PratiqueService } from './pratique.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pratique',
    templateUrl: './pratique.component.html'
})
export class PratiqueComponent implements OnInit, OnDestroy {
pratiques: Pratique[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pratiqueService: PratiqueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pratiqueService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pratiques = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPratiques();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pratique) {
        return item.id;
    }
    registerChangeInPratiques() {
        this.eventSubscriber = this.eventManager.subscribe('pratiqueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
