import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import {
    WeatherService,
    WeatherPopupService,
    WeatherComponent,
    WeatherDetailComponent,
    WeatherDialogComponent,
    WeatherPopupComponent,
    WeatherDeletePopupComponent,
    WeatherDeleteDialogComponent,
    weatherRoute,
    weatherPopupRoute,
} from './';

const ENTITY_STATES = [
    ...weatherRoute,
    ...weatherPopupRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WeatherComponent,
        WeatherDetailComponent,
        WeatherDialogComponent,
        WeatherDeleteDialogComponent,
        WeatherPopupComponent,
        WeatherDeletePopupComponent,
    ],
    entryComponents: [
        WeatherComponent,
        WeatherDialogComponent,
        WeatherPopupComponent,
        WeatherDeleteDialogComponent,
        WeatherDeletePopupComponent,
    ],
    providers: [
        WeatherService,
        WeatherPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendWeatherModule {}
