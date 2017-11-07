import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { VillePreferee } from './ville-preferee.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VillePrefereeService {

    private resourceUrl = SERVER_API_URL + 'api/ville-preferees';

    constructor(private http: Http) { }

    create(villePreferee: VillePreferee): Observable<VillePreferee> {
        const copy = this.convert(villePreferee);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(villePreferee: VillePreferee): Observable<VillePreferee> {
        const copy = this.convert(villePreferee);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<VillePreferee> {
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
     * Convert a returned JSON object to VillePreferee.
     */
    private convertItemFromServer(json: any): VillePreferee {
        const entity: VillePreferee = Object.assign(new VillePreferee(), json);
        return entity;
    }

    /**
     * Convert a VillePreferee to a JSON which can be sent to the server.
     */
    private convert(villePreferee: VillePreferee): VillePreferee {
        const copy: VillePreferee = Object.assign({}, villePreferee);
        return copy;
    }
}
