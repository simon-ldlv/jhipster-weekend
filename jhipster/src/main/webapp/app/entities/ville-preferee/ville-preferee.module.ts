import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetWeekendSharedModule } from '../../shared';
import { ProjetWeekendAdminModule } from '../../admin/admin.module';
import {
    VillePrefereeService,
    VillePrefereePopupService,
    VillePrefereeComponent,
    VillePrefereeDetailComponent,
    VillePrefereeDialogComponent,
    VillePrefereePopupComponent,
    VillePrefereeDeletePopupComponent,
    VillePrefereeDeleteDialogComponent,
    villePrefereeRoute,
    villePrefereePopupRoute,
} from './';

const ENTITY_STATES = [
    ...villePrefereeRoute,
    ...villePrefereePopupRoute,
];

@NgModule({
    imports: [
        ProjetWeekendSharedModule,
        ProjetWeekendAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VillePrefereeComponent,
        VillePrefereeDetailComponent,
        VillePrefereeDialogComponent,
        VillePrefereeDeleteDialogComponent,
        VillePrefereePopupComponent,
        VillePrefereeDeletePopupComponent,
    ],
    entryComponents: [
        VillePrefereeComponent,
        VillePrefereeDialogComponent,
        VillePrefereePopupComponent,
        VillePrefereeDeleteDialogComponent,
        VillePrefereeDeletePopupComponent,
    ],
    providers: [
        VillePrefereeService,
        VillePrefereePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendVillePrefereeModule {}
