import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Meteo } from './meteo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MeteoService {

    private resourceUrl = SERVER_API_URL + 'api/meteos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(meteo: Meteo): Observable<Meteo> {
        const copy = this.convert(meteo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(meteo: Meteo): Observable<Meteo> {
        const copy = this.convert(meteo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Meteo> {
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
     * Convert a returned JSON object to Meteo.
     */
    private convertItemFromServer(json: any): Meteo {
        const entity: Meteo = Object.assign(new Meteo(), json);
        entity.updated = this.dateUtils
            .convertLocalDateFromServer(json.updated);
        return entity;
    }

    /**
     * Convert a Meteo to a JSON which can be sent to the server.
     */
    private convert(meteo: Meteo): Meteo {
        const copy: Meteo = Object.assign({}, meteo);
        copy.updated = this.dateUtils
            .convertLocalDateToServer(meteo.updated);
        return copy;
    }
}
