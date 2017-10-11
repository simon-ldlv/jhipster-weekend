import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Pratique } from './pratique.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PratiqueService {

    private resourceUrl = SERVER_API_URL + 'api/pratiques';

    constructor(private http: Http) { }

    create(pratique: Pratique): Observable<Pratique> {
        const copy = this.convert(pratique);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pratique: Pratique): Observable<Pratique> {
        const copy = this.convert(pratique);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Pratique> {
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
     * Convert a returned JSON object to Pratique.
     */
    private convertItemFromServer(json: any): Pratique {
        const entity: Pratique = Object.assign(new Pratique(), json);
        return entity;
    }

    /**
     * Convert a Pratique to a JSON which can be sent to the server.
     */
    private convert(pratique: Pratique): Pratique {
        const copy: Pratique = Object.assign({}, pratique);
        return copy;
    }
}
