import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { VillePreferee } from './ville-preferee.model';
import { VillePrefereeService } from './ville-preferee.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-ville-preferee',
    templateUrl: './ville-preferee.component.html'
})
export class VillePrefereeComponent implements OnInit, OnDestroy {
villePreferees: VillePreferee[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private villePrefereeService: VillePrefereeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.villePrefereeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.villePreferees = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVillePreferees();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VillePreferee) {
        return item.id;
    }
    registerChangeInVillePreferees() {
        this.eventSubscriber = this.eventManager.subscribe('villePrefereeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
