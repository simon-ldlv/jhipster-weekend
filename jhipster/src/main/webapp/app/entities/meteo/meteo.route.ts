import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MeteoComponent } from './meteo.component';
import { MeteoDetailComponent } from './meteo-detail.component';
import { MeteoPopupComponent } from './meteo-dialog.component';
import { MeteoDeletePopupComponent } from './meteo-delete-dialog.component';

export const meteoRoute: Routes = [
    {
        path: 'meteo',
        component: MeteoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.meteo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'meteo/:id',
        component: MeteoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.meteo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const meteoPopupRoute: Routes = [
    {
        path: 'meteo-new',
        component: MeteoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.meteo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'meteo/:id/edit',
        component: MeteoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.meteo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'meteo/:id/delete',
        component: MeteoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.meteo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
