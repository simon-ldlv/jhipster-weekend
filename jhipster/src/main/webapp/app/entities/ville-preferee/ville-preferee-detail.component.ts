import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { VillePreferee } from './ville-preferee.model';
import { VillePrefereeService } from './ville-preferee.service';

@Component({
    selector: 'jhi-ville-preferee-detail',
    templateUrl: './ville-preferee-detail.component.html'
})
export class VillePrefereeDetailComponent implements OnInit, OnDestroy {

    villePreferee: VillePreferee;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private villePrefereeService: VillePrefereeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVillePreferees();
    }

    load(id) {
        this.villePrefereeService.find(id).subscribe((villePreferee) => {
            this.villePreferee = villePreferee;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVillePreferees() {
        this.eventSubscriber = this.eventManager.subscribe(
            'villePrefereeListModification',
            (response) => this.load(this.villePreferee.id)
        );
    }
}
