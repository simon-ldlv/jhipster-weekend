import { BaseEntity } from './../../shared';

export class Activite implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public collectif?: boolean,
        public ensoleille?: boolean,
        public nuageux?: boolean,
        public enneige?: boolean,
        public pluvieux?: boolean,
        public celsiusMin?: number,
        public celsiusMax?: number,
        public villes?: BaseEntity[],
    ) {
        this.collectif = false;
        this.ensoleille = false;
        this.nuageux = false;
        this.enneige = false;
        this.pluvieux = false;
    }
}
