/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ProjetWeekendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MeteoDetailComponent } from '../../../../../../main/webapp/app/entities/meteo/meteo-detail.component';
import { MeteoService } from '../../../../../../main/webapp/app/entities/meteo/meteo.service';
import { Meteo } from '../../../../../../main/webapp/app/entities/meteo/meteo.model';

describe('Component Tests', () => {

    describe('Meteo Management Detail Component', () => {
        let comp: MeteoDetailComponent;
        let fixture: ComponentFixture<MeteoDetailComponent>;
        let service: MeteoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjetWeekendTestModule],
                declarations: [MeteoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MeteoService,
                    JhiEventManager
                ]
            }).overrideTemplate(MeteoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MeteoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeteoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Meteo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.meteo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
