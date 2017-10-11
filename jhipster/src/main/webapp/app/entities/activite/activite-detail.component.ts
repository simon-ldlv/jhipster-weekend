import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Activite } from './activite.model';
import { ActiviteService } from './activite.service';

@Component({
    selector: 'jhi-activite-detail',
    templateUrl: './activite-detail.component.html'
})
export class ActiviteDetailComponent implements OnInit, OnDestroy {

    activite: Activite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private activiteService: ActiviteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActivites();
    }

    load(id) {
        this.activiteService.find(id).subscribe((activite) => {
            this.activite = activite;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActivites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'activiteListModification',
            (response) => this.load(this.activite.id)
        );
    }
}
