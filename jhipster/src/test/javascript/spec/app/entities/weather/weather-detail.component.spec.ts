/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ProjetWeekendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WeatherDetailComponent } from '../../../../../../main/webapp/app/entities/weather/weather-detail.component';
import { WeatherService } from '../../../../../../main/webapp/app/entities/weather/weather.service';
import { Weather } from '../../../../../../main/webapp/app/entities/weather/weather.model';

describe('Component Tests', () => {

    describe('Weather Management Detail Component', () => {
        let comp: WeatherDetailComponent;
        let fixture: ComponentFixture<WeatherDetailComponent>;
        let service: WeatherService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjetWeekendTestModule],
                declarations: [WeatherDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WeatherService,
                    JhiEventManager
                ]
            }).overrideTemplate(WeatherDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WeatherDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WeatherService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Weather(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.weather).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
