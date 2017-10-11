import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import {
    ActiviteService,
    ActivitePopupService,
    ActiviteComponent,
    ActiviteDetailComponent,
    ActiviteDialogComponent,
    ActivitePopupComponent,
    ActiviteDeletePopupComponent,
    ActiviteDeleteDialogComponent,
    activiteRoute,
    activitePopupRoute,
} from './';

const ENTITY_STATES = [
    ...activiteRoute,
    ...activitePopupRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ActiviteComponent,
        ActiviteDetailComponent,
        ActiviteDialogComponent,
        ActiviteDeleteDialogComponent,
        ActivitePopupComponent,
        ActiviteDeletePopupComponent,
    ],
    entryComponents: [
        ActiviteComponent,
        ActiviteDialogComponent,
        ActivitePopupComponent,
        ActiviteDeleteDialogComponent,
        ActiviteDeletePopupComponent,
    ],
    providers: [
        ActiviteService,
        ActivitePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendActiviteModule {}
