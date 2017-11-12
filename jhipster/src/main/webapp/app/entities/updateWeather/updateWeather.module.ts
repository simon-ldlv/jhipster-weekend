import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import {
    UpdateWeatherService,
    UpdateWeatherComponent,
    updateWeatherRoute,
} from './';

const ENTITY_STATES = [
    ...updateWeatherRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UpdateWeatherComponent,
    ],
    entryComponents: [
        UpdateWeatherComponent,
    ],
    providers: [
        UpdateWeatherService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendUpdateWeatherModule {}
