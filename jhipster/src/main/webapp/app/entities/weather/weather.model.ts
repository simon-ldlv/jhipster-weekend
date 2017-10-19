import { BaseEntity } from './../../shared';

export class Weather implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public meteos?: BaseEntity[],
        public activites?: BaseEntity[],
    ) {
    }
}
