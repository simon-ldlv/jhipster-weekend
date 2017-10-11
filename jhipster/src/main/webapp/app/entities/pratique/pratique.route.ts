import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PratiqueComponent } from './pratique.component';
import { PratiqueDetailComponent } from './pratique-detail.component';
import { PratiquePopupComponent } from './pratique-dialog.component';
import { PratiqueDeletePopupComponent } from './pratique-delete-dialog.component';

export const pratiqueRoute: Routes = [
    {
        path: 'pratique',
        component: PratiqueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.pratique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pratique/:id',
        component: PratiqueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.pratique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pratiquePopupRoute: Routes = [
    {
        path: 'pratique-new',
        component: PratiquePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.pratique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pratique/:id/edit',
        component: PratiquePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.pratique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pratique/:id/delete',
        component: PratiqueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.pratique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
