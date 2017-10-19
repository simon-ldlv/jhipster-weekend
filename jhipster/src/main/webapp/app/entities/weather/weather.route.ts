import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WeatherComponent } from './weather.component';
import { WeatherDetailComponent } from './weather-detail.component';
import { WeatherPopupComponent } from './weather-dialog.component';
import { WeatherDeletePopupComponent } from './weather-delete-dialog.component';

export const weatherRoute: Routes = [
    {
        path: 'weather',
        component: WeatherComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.weather.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'weather/:id',
        component: WeatherDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.weather.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const weatherPopupRoute: Routes = [
    {
        path: 'weather-new',
        component: WeatherPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.weather.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'weather/:id/edit',
        component: WeatherPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.weather.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'weather/:id/delete',
        component: WeatherDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.weather.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
