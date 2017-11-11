import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WeekendComponent } from './weekend.component';

export const weekendRoute: Routes = [
    {
        path: 'weekend',
        component: WeekendComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetWeekendApp.weekend.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
