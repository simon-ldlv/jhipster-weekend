import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { UpdateWeather } from './updateWeather.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UpdateWeatherService {

    private resourceUrl = SERVER_API_URL + 'api/updateWeather';

    constructor(private http: Http) { }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
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
     * Convert a returned JSON object to UpdateWeather.
     */
    private convertItemFromServer(json: any): UpdateWeather {
        const entity: UpdateWeather = Object.assign(new UpdateWeather(), json);
        return entity;
    }

    /**
     * Convert a UpdateWeather to a JSON which can be sent to the server.
     */
    private convert(updateWeather: UpdateWeather): UpdateWeather {
        const copy: UpdateWeather = Object.assign({}, updateWeather);
        return copy;
    }
}
