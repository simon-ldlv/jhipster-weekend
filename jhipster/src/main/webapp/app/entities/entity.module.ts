import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ProjetWeekendVilleModule } from './ville/ville.module';
import { ProjetWeekendActiviteModule } from './activite/activite.module';
import { ProjetWeekendPratiqueModule } from './pratique/pratique.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ProjetWeekendVilleModule,
        ProjetWeekendActiviteModule,
        ProjetWeekendPratiqueModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetWeekendEntityModule {}