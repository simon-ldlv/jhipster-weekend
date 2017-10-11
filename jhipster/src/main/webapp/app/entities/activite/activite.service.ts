import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Activite } from './activite.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ActiviteService {

    private resourceUrl = SERVER_API_URL + 'api/activites';

    constructor(private http: Http) { }

    create(activite: Activite): Observable<Activite> {
        const copy = this.convert(activite);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(activite: Activite): Observable<Activite> {
        const copy = this.convert(activite);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Activite> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Activite.
     */
    private convertItemFromServer(json: any): Activite {
        const entity: Activite = Object.assign(new Activite(), json);
        return entity;
    }

    /**
     * Convert a Activite to a JSON which can be sent to the server.
     */
    private convert(activite: Activite): Activite {
        const copy: Activite = Object.assign({}, activite);
        return copy;
    }
}
