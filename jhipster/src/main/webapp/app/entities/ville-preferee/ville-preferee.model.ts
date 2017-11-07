import { BaseEntity, User } from './../../shared';

export class VillePreferee implements BaseEntity {
    constructor(
        public id?: number,
        public ville?: BaseEntity,
        public owner?: User,
    ) {
    }
}
