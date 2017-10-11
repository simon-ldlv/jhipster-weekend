import { BaseEntity, User } from './../../shared';

export class Pratique implements BaseEntity {
    constructor(
        public id?: number,
        public activite?: BaseEntity,
        public owner?: User,
    ) {
    }
}
