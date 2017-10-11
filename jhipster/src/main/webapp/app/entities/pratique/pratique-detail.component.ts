import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Pratique } from './pratique.model';
import { PratiqueService } from './pratique.service';

@Component({
    selector: 'jhi-pratique-detail',
    templateUrl: './pratique-detail.component.html'
})
export class PratiqueDetailComponent implements OnInit, OnDestroy {

    pratique: Pratique;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pratiqueService: PratiqueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPratiques();
    }

    load(id) {
        this.pratiqueService.find(id).subscribe((pratique) => {
            this.pratique = pratique;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPratiques() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pratiqueListModification',
            (response) => this.load(this.pratique.id)
        );
    }
}
