import { BaseEntity } from './../../shared';

export class Ville implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public activites?: BaseEntity[],
        public meteo?: BaseEntity,
    ) {
    }
}
