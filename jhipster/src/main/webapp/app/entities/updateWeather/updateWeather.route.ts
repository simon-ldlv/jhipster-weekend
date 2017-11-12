import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UpdateWeatherComponent } from './updateWeather.component';

export const updateWeatherRoute: Routes = [
    {
        path: 'updateWeather',
        component: UpdateWeatherComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetUpdateWeatherApp.updateWeather.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
