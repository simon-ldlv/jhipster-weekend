import { BaseEntity } from './../../shared';

export class Activite implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public collectif?: boolean,
        public celsiusMin?: number,
        public celsiusMax?: number,
        public villes?: BaseEntity[],
        public weathers?: BaseEntity[],
    ) {
        this.collectif = false;
    }
}
