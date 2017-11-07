import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VillePrefereeComponent } from './ville-preferee.component';
import { VillePrefereeDetailComponent } from './ville-preferee-detail.component';
import { VillePrefereePopupComponent } from './ville-preferee-dialog.component';
import { VillePrefereeDeletePopupComponent } from './ville-preferee-delete-dialog.component';

export const villePrefereeRoute: Routes = [
    {
        path: 'ville-preferee',
        component: VillePrefereeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.villePreferee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ville-preferee/:id',
        component: VillePrefereeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.villePreferee.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const villePrefereePopupRoute: Routes = [
    {
        path: 'ville-preferee-new',
        component: VillePrefereePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.villePreferee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ville-preferee/:id/edit',
        component: VillePrefereePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.villePreferee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ville-preferee/:id/delete',
        component: VillePrefereeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.villePreferee.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
