import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import {
    WeekendService,
    WeekendComponent,
    weekendRoute,
} from './';

const ENTITY_STATES = [
    ...weekendRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WeekendComponent,
    ],
    entryComponents: [
        WeekendComponent,
    ],
    providers: [
        WeekendService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendWeekendModule {}
