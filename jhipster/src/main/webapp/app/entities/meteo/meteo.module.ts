import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import {
    MeteoService,
    MeteoPopupService,
    MeteoComponent,
    MeteoDetailComponent,
    MeteoDialogComponent,
    MeteoPopupComponent,
    MeteoDeletePopupComponent,
    MeteoDeleteDialogComponent,
    meteoRoute,
    meteoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...meteoRoute,
    ...meteoPopupRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MeteoComponent,
        MeteoDetailComponent,
        MeteoDialogComponent,
        MeteoDeleteDialogComponent,
        MeteoPopupComponent,
        MeteoDeletePopupComponent,
    ],
    entryComponents: [
        MeteoComponent,
        MeteoDialogComponent,
        MeteoPopupComponent,
        MeteoDeleteDialogComponent,
        MeteoDeletePopupComponent,
    ],
    providers: [
        MeteoService,
        MeteoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendMeteoModule {}
