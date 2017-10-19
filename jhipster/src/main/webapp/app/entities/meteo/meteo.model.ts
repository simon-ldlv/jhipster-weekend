import { BaseEntity } from './../../shared';

export class Meteo implements BaseEntity {
    constructor(
        public id?: number,
        public updated?: any,
        public celsiusAverage?: number,
        public ville?: BaseEntity,
        public weather?: BaseEntity,
    ) {
    }
}
