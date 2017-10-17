import { BaseEntity } from './../../shared';

export class Meteo implements BaseEntity {
    constructor(
        public id?: number,
        public celsiusMin?: number,
        public celsiusMax?: number,
        public updated?: any,
        public celsiusAverage?: number,
        public ville?: BaseEntity,
    ) {
    }
}
