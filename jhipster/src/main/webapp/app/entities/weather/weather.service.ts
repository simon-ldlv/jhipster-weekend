import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Weather } from './weather.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WeatherService {

    private resourceUrl = SERVER_API_URL + 'api/weathers';

    constructor(private http: Http) { }

    create(weather: Weather): Observable<Weather> {
        const copy = this.convert(weather);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(weather: Weather): Observable<Weather> {
        const copy = this.convert(weather);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Weather> {
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
     * Convert a returned JSON object to Weather.
     */
    private convertItemFromServer(json: any): Weather {
        const entity: Weather = Object.assign(new Weather(), json);
        return entity;
    }

    /**
     * Convert a Weather to a JSON which can be sent to the server.
     */
    private convert(weather: Weather): Weather {
        const copy: Weather = Object.assign({}, weather);
        return copy;
    }
}
