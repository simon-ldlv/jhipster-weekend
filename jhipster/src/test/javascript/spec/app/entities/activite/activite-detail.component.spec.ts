/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ProjetWeekendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ActiviteDetailComponent } from '../../../../../../main/webapp/app/entities/activite/activite-detail.component';
import { ActiviteService } from '../../../../../../main/webapp/app/entities/activite/activite.service';
import { Activite } from '../../../../../../main/webapp/app/entities/activite/activite.model';

describe('Component Tests', () => {

    describe('Activite Management Detail Component', () => {
        let comp: ActiviteDetailComponent;
        let fixture: ComponentFixture<ActiviteDetailComponent>;
        let service: ActiviteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjetWeekendTestModule],
                declarations: [ActiviteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ActiviteService,
                    JhiEventManager
                ]
            }).overrideTemplate(ActiviteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ActiviteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActiviteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Activite(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.activite).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
