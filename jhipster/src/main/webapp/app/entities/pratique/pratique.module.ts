import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import { ProjetWeekendAdminModule } from '../../admin/admin.module';
import {
    PratiqueService,
    PratiquePopupService,
    PratiqueComponent,
    PratiqueDetailComponent,
    PratiqueDialogComponent,
    PratiquePopupComponent,
    PratiqueDeletePopupComponent,
    PratiqueDeleteDialogComponent,
    pratiqueRoute,
    pratiquePopupRoute,
} from './';

const ENTITY_STATES = [
    ...pratiqueRoute,
    ...pratiquePopupRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        ProjetWeekendAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PratiqueComponent,
        PratiqueDetailComponent,
        PratiqueDialogComponent,
        PratiqueDeleteDialogComponent,
        PratiquePopupComponent,
        PratiqueDeletePopupComponent,
    ],
    entryComponents: [
        PratiqueComponent,
        PratiqueDialogComponent,
        PratiquePopupComponent,
        PratiqueDeleteDialogComponent,
        PratiqueDeletePopupComponent,
    ],
    providers: [
        PratiqueService,
        PratiquePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendPratiqueModule {}
